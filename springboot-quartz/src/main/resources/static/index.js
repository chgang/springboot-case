$(function(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:18089/job/list',

        success: function(data) {
          // alert(data);
          getInitTable(data);
        },
        error: function(err) {
            console.log(err);
        }
    });
  
  // 添加任务
  $('#addTask').click(function() {
    showDialog('flex');
      backEditStatus.set(1); // 设置添加状态
  })

  // 保存任务
  $('#dialogSave').click(function() {
    var params = $('#quart').serialize(); // 序列化表单数据
      var obj = backEditStatus.get();
      console.log(obj);
      var urlList = ['http://localhost:18089/job/add', 'http://localhost:18089/job/edit'];
      var url = urlList[obj.status - 1];
      if (obj.status == 2 && obj.id) {
          params = params + '&id=' + obj.id;
      }
      console.log(url, params);
    $.ajax({
      url: url,
      type: 'post',
      data: params,
      success: function(data) {
        // console.log(data);
          getInitTable(data);
      },
      error: function(err) {
        console.log(err);
      }
    })
    showDialog('none');
    alertInfo('success', '操作成功！');
  })

  // 取消任务
  $('#dialogCancel').click(function() {
    showDialog('none');
    initDialogData();
  })
})


// 显示隐藏添加任务弹窗
function showDialog(state) {
  $('#dialog').css('display', state);
}

// 获取运行中数据
function getInitTable(data) {
  var html = "";
  $('#tableBody').html("");
  if (data.length === 0) {
    html = `
      <tr id="noData" class="no-data">
        <td colspan="11">暂无数据</td>
      </tr>
    `
    $('#tableBody').append(html);
  } else {
    $.each(data, function(key, item) {
      html += `
        <tr>
          <td>${item.jobName}</td>
          <td>${item.aliasName}</td>
          <td>${item.jobGroup}</td>
          <td>${item.jobTrigger}</td>
          <td>${item.status}</td>
          <td>${item.cronExpression}</td>
          <td>${item.sync == 1 ? '同步' : '异步'}</td>
          <td>${item.url}</td>
          <td>${item.description}</td>
          <td>${item.param}</td>
          <td>
            <button type="button" class="btn btn-danger" id="diaologSave" onclick="operate(${item.id}, 'pause')">暂停</button>
            <button type="button" class="btn btn-success" id="diaologSave" onclick="operate(${item.id}, 'restart')">恢复</button>
            <button type="button" class="btn btn-success" id="diaologSave" onclick="operate(${item.id}, 'run')">运行一次</button>
            <button type="button" class="btn btn-primary" id="diaologSave" onclick="operate(${item.id}, 'edit')">修改</button>
            <button type="button" class="btn btn-danger" id="diaologSave" onclick="operate(${item.id}, 'delete')">删除</button>
          </td>
        </tr>
      `
    })
    $('#tableBody').append(html);
  }
}

// 操作数据
function operate(id, type) {
  switch(type) {
    case 'pause':
      alertInfo('success', '暂停');
        $.ajax({
            url: 'http://localhost:18089/job/pause',
            type: 'post',
            data: {id:id},
            success: function(data) {
                // alert(data);
                getInitTable(data);
            },
            error: function(err) {
                console.log(err);
            }
        })
      break;
    case 'restart':
      alertInfo('success', '恢复');
        $.ajax({
            url: 'http://localhost:18089/job/resume',
            type: 'post',
            data: {id:id},
            success: function(data) {
              // alert(data);
                getInitTable(data);
            },
            error: function(err) {
                console.log(err);
            }
        })
      break;
    case 'run':
      alertInfo('success', '运行一次');
        $.ajax({
            url: 'http://localhost:18089/job/runOnce',
            type: 'post',
            data: {id:id},
            success: function(data) {
                // alert(data);
                getInitTable(data);
            },
            error: function(err) {
                console.log(err);
            }
        })
      break;
    case 'edit':
      // 此处模拟获得数据 
        alertInfo('success', '修改');
        showDialog('flex'); // 显示弹窗
        backEditStatus.set(2, id);
        $.ajax({
            url: 'http://localhost:18089/job/get',
            type: 'post',
            data: {id:id},
            success: function(data) {
                // alert(data);
                initDialogData(data);  // 填充数据
            },
            error: function(err) {
                console.log(err);
            }
        })
        // $.ajax({
        //     url: 'http://localhost:18089/job/edit',
        //     type: 'post',
        //     data: {id:id},
        //     success: function(data) {
        //         // alert(data);
        //         getInitTable(data)
        //     },
        //     error: function(err) {
        //         console.log(err);
        //     }
        // })
      break;
    case 'delete':
        $.ajax({
            url: 'http://localhost:18089/job/delete',
            type: 'post',
            data: {id:id},
            success: function(data) {
                // alert(data);
                getInitTable(data);
            },
            error: function(err) {
                console.log(err);
            }
        })
      break;
    
  }
}

// 弹窗信息
var alertInfo = (function() {
  var timer = null;
  var clear = function() {
    $('.alert').remove();
    clearTimeout(timer);
    timer = null;
  }
  // back
  return function(type, msg) {
    clear()
    var html = `<div class="alert alert-${type}" role="alert">${msg}</div>`
    $('body').prepend(html);
    timer = setTimeout(function(){
      clear();
    }, 2000)
  }
})()

// 初始化弹窗数据
function initDialogData(param) {
  var keys = ['jobName', 'jobGroup', 'aliasName', 'cronExpression', 'sync', 'url', 'description', 'param']; // id 列表
  $.each(keys, function(k, v){
    var val = param && param[v] ? param[v] : ''; 
    $(`#${v}`) && $(`#${v}`).val(val);
  })
  // 同步异步操作
  if (param && param['sync']) {
    $('input[type="radio"]').eq(param['sync']-1).prop('checked', true);
  }
}

var backEditStatus = (function() {
    var status = 1; //判断保存操作是新增还是编辑 1 新增 2 添加 默认新增
    var id = "";  //编辑状态时的id
    return {
        set: function(st, editId) {
            status = st;
            id = editId;
        },
        get: function() {
            return {
                status: status,
                id: id
            };
        }
    }
})()