package it.unisa.gitdm.metrics.parser;

import it.unisa.gitdm.metrics.parser.bean.MethodBean;

class InvocationParser {

    public static MethodBean parse(String pInvocationName) {
        MethodBean methodBean = new MethodBean();
        methodBean.setName(pInvocationName);
        return methodBean;
    }

}
