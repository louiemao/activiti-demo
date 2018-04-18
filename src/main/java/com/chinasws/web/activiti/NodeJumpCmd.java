//package com.chinasws.web.activiti;
//
//import org.activiti.bpmn.model.*;
//import org.activiti.bpmn.model.Process;
//import org.activiti.engine.delegate.event.ActivitiEventDispatcher;
//import org.activiti.engine.delegate.event.ActivitiEventType;
//import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
//import org.activiti.engine.impl.context.Context;
//import org.activiti.engine.impl.identity.Authentication;
//import org.activiti.engine.impl.interceptor.Command;
//import org.activiti.engine.impl.interceptor.CommandContext;
//import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
//import org.activiti.engine.impl.persistence.entity.TaskEntity;
//import org.activiti.engine.impl.util.CollectionUtil;
//import org.activiti.engine.impl.util.ProcessDefinitionUtil;
//
//import java.util.Map;
//
//
//public class NodeJumpCmd implements Command<Void> {
//    private String currentTaskId;
//    private FlowNode targetFlowNode;
//    private Map<String, Object> variables;
//    private Map<String, Object> transientVariables;
//    private boolean localScope;
//
//    public NodeJumpCmd(String currentTaskId, FlowNode targetFlowNode) {
//        this.currentTaskId = currentTaskId;
//        this.targetFlowNode = targetFlowNode;
//    }
//
//    public NodeJumpCmd(String currentTaskId, FlowNode targetFlowNode, Map<String, Object> variables) {
//        this.currentTaskId = currentTaskId;
//        this.targetFlowNode = targetFlowNode;
//        this.variables = variables;
//    }
//
//    public NodeJumpCmd(String currentTaskId, FlowNode targetFlowNode, Map<String, Object> variables, boolean localScope) {
//        this(currentTaskId, targetFlowNode, variables);
//        this.localScope = localScope;
//    }
//
//    public NodeJumpCmd(String currentTaskId, FlowNode targetFlowNode, Map<String, Object> variables, Map<String, Object> transientVariables) {
//        this(currentTaskId, targetFlowNode, variables);
//        this.transientVariables = transientVariables;
//    }
//
//    @Override
//    public Void execute(CommandContext commandContext) {
//        TaskEntity taskEntity = (TaskEntity) commandContext.getTaskEntityManager().findById(this.currentTaskId);
//
//        if (this.variables != null) {
//            if (this.localScope) {
//                taskEntity.setVariablesLocal(this.variables);
//            } else if (taskEntity.getExecutionId() != null) {
//                taskEntity.setExecutionVariables(this.variables);
//            } else {
//                taskEntity.setVariables(this.variables);
//            }
//        }
//
//        if (this.transientVariables != null) {
//            if (this.localScope) {
//                taskEntity.setTransientVariablesLocal(this.transientVariables);
//            } else {
//                taskEntity.setTransientVariables(this.transientVariables);
//            }
//        }
//
//        commandContext.getProcessEngineConfiguration().getListenerNotificationHelper().executeTaskListeners(taskEntity, "complete");
//        if (Authentication.getAuthenticatedUserId() != null && taskEntity.getProcessInstanceId() != null) {
//            ExecutionEntity processInstanceEntity = (ExecutionEntity) commandContext.getExecutionEntityManager().findById(taskEntity.getProcessInstanceId());
//            commandContext.getIdentityLinkEntityManager().involveUser(processInstanceEntity, Authentication.getAuthenticatedUserId(), "participant");
//        }
//
//        ActivitiEventDispatcher eventDispatcher = Context.getProcessEngineConfiguration().getEventDispatcher();
//        if (eventDispatcher.isEnabled()) {
//            if (variables != null) {
//                eventDispatcher.dispatchEvent(ActivitiEventBuilder.createEntityWithVariablesEvent(ActivitiEventType.TASK_COMPLETED, taskEntity, variables, localScope));
//            } else {
//                eventDispatcher.dispatchEvent(ActivitiEventBuilder.createEntityEvent(ActivitiEventType.TASK_COMPLETED, taskEntity));
//            }
//        }
//
//        commandContext.getTaskEntityManager().deleteTask(taskEntity, "jump", false, true);
//        if (taskEntity.getExecutionId() != null) {
//            ExecutionEntity executionEntity = (ExecutionEntity) commandContext.getExecutionEntityManager().findById(taskEntity.getExecutionId());
//
//            String processDefinitionId = executionEntity.getProcessDefinitionId();
//            Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
//            String activityId = executionEntity.getCurrentActivityId();
//            FlowNode flowNode = (FlowNode) process.getFlowElement(activityId, true);
//            if (!executionEntity.isProcessInstanceType()) {
//                if (CollectionUtil.isNotEmpty(flowNode.getExecutionListeners())) {
//                    commandContext.getProcessEngineConfiguration().getListenerNotificationHelper().executeExecutionListeners(flowNode, executionEntity, "end");
//                }
//                commandContext.getHistoryManager().recordActivityEnd(executionEntity, (String) null);
//            }
//
//
//            executionEntity.setCurrentFlowElement(targetFlowNode);
//            Context.getAgenda().planContinueProcessOperation(executionEntity);
//        }
//        return null;
//
//
//    }
//}
