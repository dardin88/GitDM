package it.unisa.gitdm.metrics.parser.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public final class ClassBean implements Comparable {

    private String name;
    private Collection<InstanceVariableBean> instanceVariables;
    private Collection<MethodBean> methods;
    private Collection<String> imports;
    private String textContent;
    private int LOC;
    private String superclass;
    private String belongingPackage;

    public ClassBean() {
        name = null;
        instanceVariables = new ArrayList<>();
        methods = new ArrayList<>();
        setImports(new ArrayList<String>());
    }

    public int getLOC() {
        return LOC;
    }

    public void setLOC(int lOC) {
        LOC = lOC;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public Collection<InstanceVariableBean> getInstanceVariables() {
        return instanceVariables;
    }

    public void setInstanceVariables(Collection<InstanceVariableBean> pInstanceVariables) {
        instanceVariables = pInstanceVariables;
    }

    public void addInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.add(pInstanceVariable);
    }

    public void removeInstanceVariables(InstanceVariableBean pInstanceVariable) {
        instanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethods() {
        return methods;
    }

    public void setMethods(Collection<MethodBean> pMethods) {
        methods = pMethods;
    }

    public void addMethod(MethodBean pMethod) {
        methods.add(pMethod);
    }

    public void removeMethod(MethodBean pMethod) {
        methods.remove(pMethod);
    }

    @Override
    public String toString() {
        return "name = " + name + "\n"
                + "instanceVariables = " + instanceVariables + "\n"
                + "methods = " + methods + "\n";
    }

    @Override
    public int compareTo(Object pClassBean) {
        return this.getName().compareTo(((ClassBean) pClassBean).getName());
    }

    public Collection<String> getImports() {
        return imports;
    }

    public void setImports(Collection<String> imports) {
        this.imports = imports;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    private String getBelongingPackage() {
        return belongingPackage;
    }

    public void setBelongingPackage(String belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    @Override
    public boolean equals(Object arg) {
        if (arg instanceof ClassBean) {
            return this.getName().equals(((ClassBean) arg).getName())
                    && this.getBelongingPackage().equals(((ClassBean) arg).getBelongingPackage());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.belongingPackage);
        return hash;
    }

}
