package com.xt.landlords.statemachine;//package com.xt.landlords.statemachine;
//
//import com.xt.landlords.game.regular.GameRegularModel;
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.squirrelframework.foundation.component.SquirrelPostProcessor;
//import org.squirrelframework.foundation.fsm.HistoryType;
//import org.squirrelframework.foundation.fsm.StateMachineBuilder;
//import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
//import org.squirrelframework.foundation.fsm.annotation.*;
//
///**
// * Created by leo on 17/4/24.
// */
//public class GameCommonState {
//
//
//    enum PEvent {
//        A2B, B2C, A2C, B2A, on2B
//    }
//
//
//    @States({
//            @State(name = "parent", historyType = HistoryType.DEEP),
//            @State(parent = "parent", name = "child1", entryCallMethod = "entryChild1", exitCallMethod = "exitChild1",
//                    initialState = true),
//            @State(parent = "parent", name = "child2"),
//            @State(name = "B", entryCallMethod = "enterB", exitCallMethod = "exitB"),
//            @State(name = "C", entryCallMethod = "enterC", exitCallMethod = "exitC"),
//    })
//    @Transitions({
//            @Transit(from = "parent", to = "C", on = "A2B", callMethod = "transitA2B"),
//            @Transit(from = "child1", to = "B", on = "A2B", callMethod = "transitA2B", when = MyCondition.class),
//            @Transit(from = "B", to = "child2", on = "B2A", callMethod = "transitB2A"),
//            @Transit(from = "B", to = "C", on = "B2C", callMethod = "transitB2C")
//
//    })
//
//
//    @ContextEvent(finishEvent = "A2C")
//    static class ParallelStateMachine extends GameController<GameRegularModel, ParallelStateMachine, PState, PEvent,
//            MyContext> {
//        private StringBuilder logger = new StringBuilder();
//
//        public void transitA2B(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("transitA2B");
//            context.setField1("abc");
//            //System.out.println("transitA2B:" + stateMachine.getCurrentState());
//        }
//
//        public void transitB2A(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("transitB2A");
//        }
//
//        public void transitB2C(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("transitB2C");
//        }
//
//        public void transitA2C(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("transitA2C");
//        }
//
//        public void enterA(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("enterA");
//            System.out.println("enterA:" + stateMachine.getCurrentState());
//        }
//
//        public void entryChild1(PState from, PState to, PEvent event, MyContext context) throws Exception {
//            logger.append("entryChild1");
//            System.out.println("entryChild1:" + stateMachine.getCurrentState());
////            throw new Exception("dsfsdf");
//        }
//
//        public void exitChild1(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("exitChild1");
//            System.out.println("exitChild1:" + stateMachine.getCurrentState());
//        }
//
//        @Override
//        protected void afterTransitionCausedException(PState fromState, PState toState, PEvent event, MyContext
//                context) {
//            super.afterTransitionCausedException(fromState, toState, event, context);
//        }
//
//        public void exitA(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("exitA");
//            System.out.println("exitA:" + stateMachine.getCurrentState());
//        }
//
//        public void enterB(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("enterB");
//            System.out.println("enterB:" + stateMachine.getCurrentState());
//        }
//
//        public void exitB(PState from, PState to, PEvent event, MyContext context) {
//            System.out.println("exitB:" + stateMachine.getCurrentState());
//            logger.append("exitB");
//        }
//
//        public void enterC(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("enterC");
//        }
//
//        public void exitC(PState from, PState to, PEvent event, MyContext context) {
//            logger.append("exitC");
//        }
//
//        @Override
//        protected void beforeActionInvoked(PState from, PState to, PEvent event, MyContext context) {
//            addOptionalDot();
//        }
//
//        @Override
//        public PEvent getFirstEvent() {
//            return PEvent.A2B;
//        }
//
//        @Override
//        public PState getInitState() {
//            return PState.B;
//        }
//
//        @Override
//        public Integer getGameType() {
//            return 1000;
//        }
//
//        private void addOptionalDot() {
//            if (logger.length() > 0) {
//                logger.append('.');
//            }
//        }
//
//        public String consumeLog() {
//            final String result = logger.toString();
//            logger = new StringBuilder();
//            return result;
//        }
//
//    }
//
//    static ParallelStateMachine stateMachine;
//
//    static class MyStateMachinePostProcessor implements SquirrelPostProcessor<ParallelStateMachine> {
//
//        public MyStateMachinePostProcessor() {
//
//        }
//
//        @Override
//        public void postProcess(ParallelStateMachine component) {
//            System.out.println("Post Processor");
//        }
//    }
//
//    public static void main(String[] args) {
////        StateMachineBuilder<ParallelStateMachine, PState, PEvent, MyContext> builder = StateMachineBuilderFactory
//// .create
////                (ParallelStateMachine.class, PState.class, PEvent.class, MyContext.class);
////        SquirrelPostProcessorProvider.getInstance().register(ParallelStateMachine.class, MyStateMachinePostProcessor
////                .class);
////        stateMachine = builder.newStateMachine(PState.A);
//
//
//        StateMachineBuilder<ParallelStateMachine, PState, PEvent, MyContext> builder =
//                StateMachineBuilderFactory.<ParallelStateMachine, PState, PEvent, MyContext>
//                        create(ParallelStateMachine.class, PState.class,
//                        PEvent.class, MyContext.class, ParallelStateMachine.class);
//        stateMachine = builder.newStateMachine(PState.child1);
//
//
//        MyContext context = new MyContext().setField1("field11111").setField2(12);
//        try {
//            //stateMachine.start(context);
//            PState test = stateMachine.test(PEvent.B2A, context);
//            stateMachine.fire(PEvent.A2B, context);
//            System.out.println("current Status:" + stateMachine.getCurrentState());
//        } catch (Exception ex) {
//            System.out.println(ExceptionUtils.getFullStackTrace(ex));
//        }
////        System.out.println("after start:" + stateMachine.getCurrentState());
////        stateMachine.fire(PEvent.A2B, context);
////        System.out.println("fire A2B:" + stateMachine.getCurrentState());
//        //stateMachine.fire(PEvent.B2C, context);
//        //System.out.println("fire B2C:"+ stateMachine.getCurrentState());
//        //stateMachine.terminate();
////        System.out.println(stateMachine.getCurrentState());
////        StateMachineData.Reader<ParallelStateMachine, PState, PEvent, MyContext>
////                parallelStateMachinePStatePEventIntegerReader = stateMachine.dumpSavedData();
////        String serialize = ObjectSerializableSupport.serialize(parallelStateMachinePStatePEventIntegerReader);
////        StateMachineData.Reader<ParallelStateMachine, PState, PEvent, MyContext> loadedSavedData =
////                ObjectSerializableSupport.deserialize(serialize);
////        ParallelStateMachine stateMachineLoad = builder.newStateMachine(PState.A);
////        boolean b = stateMachineLoad.loadSavedData(loadedSavedData);
////        MyContext integer = stateMachineLoad.getData().read().startContext();
////        System.out.println(integer.getField1());
//        //System.out.println(serialize);
//
//    }
//}