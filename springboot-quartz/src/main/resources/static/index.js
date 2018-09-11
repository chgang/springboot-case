$(function(){
  getInitTable(mockData);
  
  // 添加任务
  $('#addTask').click(function() {
    showDialog('flex');
  })

  // 保存任务
  $('#dialogSave').click(function() {
    var params = $('#quart').serialize(); // 序列化表单数据
    console.log(params);
    // $.ajax({
    //   url: '',
    //   type: 'get',
    //   data: params,
    //   success: function(data) {
    //     console.log(data);
    //   },
    //   error: function(err) {
    //     console.log(err);
    //   }
    // })
    showDialog('none');
    alertInfo('success', '操作成功！');
  })

  // 取消任务
  $('#dialogCancel').click(function() {
    showDialog('none');
    initDialogData();
  })
})

  // 模拟数据
  var mockData = [
    {
      id: '222',
      taskName: "某某任务111",
      taskAlias: "某某任务别名",
      taskGroup: "某某任务分组",
      taskTrigger: "触发器",
      taskStatus: "运行",
      taskTimer: "xxxxxx",
      taskSync: "1",
      taskUrl: "执行",
      taskDesc: "任务22222",
      taskParams: "参数1，参数2",
    },
    {
      id: "111",
      taskName: "某某任务2222",
      taskAlias: "某某任务别名2",
      taskGroup: "某某任务分组2",
      taskTrigger: "触发器2",
      taskStatus: "运行2",
      taskTimer: "xxxxxx2",
      taskSync: "2",
      taskUrl: "执行2",
      taskDesc: "任务222223",
      taskParams: "参数1，参数24，参数24，参数24",
    }
  ];

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
          <td>${item.taskName}</td>
          <td>${item.taskAlias}</td>
          <td>${item.taskGroup}</td>
          <td>${item.taskTrigger}</td>
          <td>${item.taskStatus}</td>
          <td>${item.taskTimer}</td>
          <td>${item.taskSync == 1 ? '同步' : '异步'}</td>
          <td>${item.taskUrl}</td>
          <td>${item.taskDesc}</td>
          <td>${item.taskParams}</td>
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