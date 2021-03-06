<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>分布式定时任务</title>
  <link href="https://cdn.bootcss.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/index.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/reset.css" rel="stylesheet">
  <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/index.js"></script>
</head>

<body>
  <div class="content">
    <div class="content__header">
      <h1>Springboot Quartz分布式定时任务</h1>
      <p>Springboot整合Quartz基于数据库的分布式定时任务，可动态添加、删除、修改定时任务。</br>
        该项目借鉴了github部分开源项目并改造，如发现雷同，请勿吐槽！谢谢大家支持！</p>
    </div>
    <div class="content__body">
      <div class="content__btn-con">
        <button id="addTask" type="button" class="btn btn-success">添加任务</button>
      </div>
      <h4>运行中的任务</h4>
      <div class="content__table-con table-responsive">
        <table class="table table-bordered table-scroll">
          <thead>
            <tr>
              <th width="100">任务名称</th>
              <th width="100">任务别名</th>
              <th width="100">任务分组</th>
              <th width="100">触发器</th>
              <th width="100">任务状态</th>
              <th width="150">时间表达式</th>
              <th width="100">是否异步</th>
              <th width="100">任务执行</th>
              <th width="100">任务描述</th>
              <th width="100">执行参数</th>
              <th width="400">操作</th>
            </tr>
          </thead>
          <tbody id="tableBody">
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <div class="dialog-con" id="dialog">
    <div class="dialog-con__body">
      <h4>添加任务</h4>
      <form class="form-horizontal" id="quart">
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务名称</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="jobName" name="jobName">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务分组</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="jobGroup" name="jobGroup">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务别名</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="aliasName" name="aliasName">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">时间表达式</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="cronExpression" name="cronExpression">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">是否异步</label>
          <div class="col-sm-9">
            <input type="radio" id="syncSync" name="sync" value="1">
            <label class="control-label" for="syncSync">同步</label>
            <input type="radio" name="sync" value="0" id="syncAsync">
            <label  class="control-label" for="syncAsync">异步</label>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务执行url</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="url" name="url">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务执行描述</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="description" name="description">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-3 control-label">任务执行参数</label>
          <div class="col-sm-9">
            <input type="text" class="form-control" id="param" name="param">
          </div>
        </div>
        <div class="form-group row">
          <div class="col-sm-offset-2 col-sm-10">
              <button type="button" class="btn btn-success" id="dialogSave">保存</button>
              <button type="button" class="btn btn-info" id="dialogCancel">取消</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</body>

</html>