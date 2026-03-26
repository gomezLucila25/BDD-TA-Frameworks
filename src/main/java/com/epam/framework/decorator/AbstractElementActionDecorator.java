package com.epam.framework.decorator;

import org.openqa.selenium.WebElement;

/**
 * PATTERN: Decorator — abstract base decorator.
 *
 * Holds a reference to another ElementAction and delegates perform() to it.
 * Subclasses override perform() to add behaviour before/after the delegation.
 */
public abstract class AbstractElementActionDecorator implements ElementAction {

    protected final ElementAction wrapped;

    protected AbstractElementActionDecorator(ElementAction wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void perform(WebElement element) {
        wrapped.perform(element);
    }
}
