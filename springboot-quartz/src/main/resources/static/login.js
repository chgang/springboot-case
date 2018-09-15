$(function() {
  $('#login').click(function() {
    console.log(2);
    var user = $('#user').val();
    var pass = $('#pass').val();
    console.log()
    if (user == '') {
      console.log(23);
      $('#user').addClass('error--border');
      return $('#user_msg').html('请输入用户名');
    }
    if (pass == '') {
      $('#pass').addClass('error--border');
      $('#pass_msg').html('请输入密码');
    }
    alertInfo('success', '登录成功！');
    setTimeout(function(){
      location.href="index.html";
    }, 1000);
    // $.ajax({
    //   url: '////',
    //   type: 'post',
    //   data: {user: user, pass: pass},
    //   success: function(res) {
    //     console.log(res);
    //   },
    //   error: function(err) {
    //     console.log(err);
    //   }
    // })
  })


})

function hideMsg(id) {
  $(`#${id}_msg`).html('');
  $(`#${id}`).removeClass('error--border');
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