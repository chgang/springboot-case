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
      return $('#pass_msg').html('请输入密码');
    }
    $.ajax({
        url: 'http://localhost:18089/job/user/login',
        type: 'post',
        data: {user: user, pass: pass},
        success: function(res) {

            if (res.code == 0){
                alertInfo("success","登录成功!");
                location.href="index"
            } else {
                alertInfo("failed", "登录失败!");
            }
        },
        error: function(err) {
          console.log('错误')
          console.log(err);
        }
      });

    setTimeout(function(){
        console.log("超时")
      location.href="toLogin";
    }, 1000);
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