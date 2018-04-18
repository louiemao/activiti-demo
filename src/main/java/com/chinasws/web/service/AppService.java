package com.chinasws.web.service;

import com.alibaba.fastjson.JSON;
import com.chinasws.web.dao.ApplicationDao;
import com.chinasws.web.entity.Application;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/16.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;


    public void submit(Application app, String result, String pid, String tid) throws Exception {
        // 保存申请单数据
        if (app == null) {
            throw new Exception("申请单信息不能为空");
        }
        if (app.getId() == null) {
            //第一次保存
            app.setId(UUID.randomUUID().toString().replace("-", ""));
            applicationDao.insert(app);
        } else {
            Application entity = applicationDao.selectByPrimaryKey(app.getId());
            entity.setFormId(app.getFormId());
            entity.setProjectCd(app.getProjectCd());
            entity.setProjectName(app.getProjectName());
            entity.setRequirement(app.getRequirement());
            applicationDao.updateByPrimaryKey(entity);
            app = entity;
        }

        Map<String, Object> variables = new HashMap<>();
        if (StringUtils.isBlank(pid)) {
            //创建流程实例
            variables.put("apply", "admin");
            variables.put("kszr", "admin");
            variables.put("bmz", "admin");
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test", variables);
            pid = processInstance.getId();
            Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
            tid = task.getId();
            app.setPid(pid);
            applicationDao.updateByPrimaryKey(app);
        }
        variables.put("result", result);
        taskService.complete(tid,variables);
    }

    public Map getTasks(String userCd, int start, int limit) {
        Map map = new HashMap();
        List<Map> data = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey("test").listPage(start, limit);

        if (tasks != null && tasks.size() > 0) {
            for (int index = 0, count = tasks.size(); index < count; index++) {
                Task task = tasks.get(index);
                Application application = applicationDao.selectByPid(task.getProcessInstanceId());
                if (application == null) {
                    continue;
                }
                Map dto = new HashMap();
                dto.put("stepname", task.getName());
                dto.put("pid", task.getProcessInstanceId());
                dto.put("tid", task.getId());
                dto.put("formkey", task.getFormKey());
                dto.put("lastperson", historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().orderByHistoricTaskInstanceEndTime().desc().list().get(0).getAssignee());
                dto.put("createtime", simpleDateFormat.format(task.getCreateTime()));
                dto.put("app", application);
                data.add(dto);
            }
        }

        map.put("count", taskService.createTaskQuery().processDefinitionKey("test").count());
        map.put("data", data);
        return map;
    }

    public Map getAppByPid(String pid) {
        Application app = applicationDao.selectByPid(pid);
        Map map = (Map) JSON.toJSON(app);
        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
        String currentStepName = task.getName();
        map.put("currentStepName", currentStepName);
        return map;
    }

    public String taskRollback(String taskId){
//        //根据要跳转的任务ID获取其任务
//        HistoricTaskInstance historicTaskInstance=historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
//        //获取流程实例
//        ProcessInstance instance=runtimeService.createProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
//        //取得流程定义
//        ProcessDefinitionEntity definition=(ProcessDefinitionEntity)repositoryService.getProcessDefinition(historicTaskInstance.getProcessDefinitionId());
//        //获取历史任务的Activity
//        ActivityImpl hisActivity=definition.
        return null;
    }
}
