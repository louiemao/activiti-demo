//package com.chinasws.web;
//
//import com.chinasws.web.activiti.NodeJumpCmd;
//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.bpmn.model.FlowElement;
//import org.activiti.bpmn.model.FlowNode;
//import org.activiti.engine.*;
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.repository.Deployment;
//import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.task.Task;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ActivitiApplicationTests {
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private RepositoryService repositoryService;
//    @Autowired
//    private HistoryService historyService;
//    @Autowired
//    private ManagementService managementService;
//
//    @Test
//    public void contextLoads() {
//        //部署流程定义
//        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/test.bpmn20.xml").deploy();
//
//        // 创建流程
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("apply", "admin");
//        variables.put("kszr", "admin");
//        variables.put("bmz", "admin");
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test", variables);
//        String pid = processInstance.getId();
//
//        //获取并完成第一个节点
//        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        String tid = task.getId();
//        taskService.complete(tid, variables);
//
//        //获取并完成第二个节点
//        Task task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        String tid2 = task2.getId();
//        variables.put("result", "不同意");
//        taskService.complete(tid2, variables);
//
//        //获取并完成第一个节点
//        task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        tid = task.getId();
//        taskService.complete(tid, variables);
//
//        //获取并完成第二个节点
//        task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        tid2 = task2.getId();
//        variables.put("result", "同意");
//        taskService.complete(tid2, variables);
//
//        //获取并完成第三个节点
//        Task task3 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        String tid3 = task3.getId();
//        variables.put("result", "同意");
//        taskService.complete(tid3, variables);
//
//        //核实流程是否结束
//        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
//
////        List<HistoricIdentityLink> list= historyService.getHistoricIdentityLinksForProcessInstance("25007");
//
//        System.out.println();
//    }
//
//    //测试自定义跳转
//    @Test
//    public void contextLoads2() {
//        //部署流程定义
//        Deployment deployment = repositoryService.createDeployment().addClasspathResource("processes/test.bpmn20.xml").deploy();
//
//        // 创建流程
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("apply", "admin");
//        variables.put("kszr", "admin");
//        variables.put("bmz", "admin");
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("test", variables);
//        String pid = processInstance.getId();
//
//        //获取并完成第一个节点
//        Task task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        String tid = task.getId();
//        taskService.complete(tid, variables);
//
//        //回到节点1
//        Task task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(task2.getProcessDefinitionId());
//        FlowNode flowElement = (FlowNode)bpmnModel.getFlowElement("task1");
//        managementService.executeCommand(new NodeJumpCmd(task2.getId(), flowElement));
////        Task task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        taskService.deleteTask(task2.getId());
////        runtimeService.trigger("task1");
////
////        //取得流程定义
////
////        ProcessDefinitionEntityImpl definition =(ProcessDefinitionEntityImpl) repositoryService.getProcessDefinition(historicTaskInstance.getProcessDefinitionId());
//
////        //获取历史任务的Activity
////        ActivityImpl hisActivity=definition.
//
//        //撤销第一个节点的办理
////        Task task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        taskService.deleteTask(task2.getId(), true);
////        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(task.getId()).singleResult();
////        historyService.deleteHistoricTaskInstance(historicTaskInstance.getId());
//
//
////        Execution execution = runtimeService.createExecutionQuery().processInstanceId(pid).activityId("task1").singleResult();
////        taskService.delegateTask();
//
//
////        //获取并完成第二个节点
////        Task task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        String tid2 = task2.getId();
////        variables.put("result", "不同意");
////        taskService.complete(tid2, variables);
////
////        //获取并完成第一个节点
////        task = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        tid = task.getId();
////        taskService.complete(tid, variables);
////
////        //获取并完成第二个节点
////        task2 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        tid2 = task2.getId();
////        variables.put("result", "同意");
////        taskService.complete(tid2, variables);
////
////        //获取并完成第三个节点
////        Task task3 = taskService.createTaskQuery().processInstanceId(pid).singleResult();
////        String tid3 = task3.getId();
////        variables.put("result", "同意");
////        taskService.complete(tid3, variables);
////
////        //核实流程是否结束
////        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
//
//
//        System.out.println();
//    }
//}
