$(function(){
    $.ajax({
        type: 'POST',
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
  })

  // 保存任务
  $('#dialogSave').click(function() {
    var params = $('#quart').serialize(); // 序列化表单数据
    console.log(params);
    $.ajax({
      url: 'http://localhost:18089/job/add',
      type: 'post',
      data: params,
      success: function(data) {
        console.log(data);
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
    $.each(JSON.parse(data), function(key, item) {
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
      break;
    case 'restart':
      alertInfo('success', '恢复');
      break;
    case 'run':
      alertInfo('success', '运行一次');
      break;
    case 'edit':
      // 此处模拟获得数据 
      var data = mockData.filter(item => item.id == id)[0];
      initDialogData(data); // 填充数据
      showDialog('flex'); // 显示弹窗
      // $.ajax({
      //   ...
      // })
      break;
    case 'delete':
      console.log(id);
      var data = mockData = mockData.filter(item => item.id != id);
      getInitTable(data) //重新获取一遍数据
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
  var keys = ['taskName', 'taskGroup', 'taskAlias', 'taskTimer', 'taskSync', 'taskUrl', 'taskDesc', 'taskParams']; // id 列表
  $.each(keys, function(k, v){
    var val = param && param[v] ? param[v] : ''; 
    $(`#${v}`) && $(`#${v}`).val(val);
  })
  // 同步异步操作
  if (param && param['taskSync']) {
    $('input[type="radio"]').eq(param['taskSync']-1).prop('checked', true);
  }
}