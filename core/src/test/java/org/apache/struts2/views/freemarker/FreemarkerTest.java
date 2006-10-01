/*
 * $Id$
 *
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.struts2.views.freemarker;

import java.util.List;

import junit.framework.TestCase;

import org.apache.struts2.util.ListEntry;
import org.apache.struts2.util.StrutsUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

import freemarker.ext.beans.CollectionModel;
import freemarker.template.ObjectWrapper;


/**
 */
public class FreemarkerTest extends TestCase {

    TestAction testAction = null;


    /**
     *
     */
    public FreemarkerTest(String name) {
        super(name);
    }


    public void testSelectHelper() {
        StrutsUtil wwUtil = new StrutsUtil(ActionContext.getContext().getValueStack(), null, null);

        List selectList = null;

        selectList = wwUtil.makeSelectList("ignored", "stringList", null, null);
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("one", ((ListEntry) selectList.get(0)).getValue());

        selectList = wwUtil.makeSelectList("ignored", "beanList", "name", "value");
        assertEquals("one", ((ListEntry) selectList.get(0)).getKey());
        assertEquals("1", ((ListEntry) selectList.get(0)).getValue());
    }

    public void testValueStackMode() throws Exception {
        ScopesHashModel model = new ScopesHashModel(ObjectWrapper.BEANS_WRAPPER, null, null, ActionContext.getContext().getValueStack());

        CollectionModel stringList = null;

        stringList = (CollectionModel) model.get("stringList");
        assertEquals("one", stringList.get(0).toString());

        assertEquals("one", model.get("stringList[0]").toString());
        assertEquals("one", model.get("beanList[0].name").toString());
    }

    protected void setUp() throws Exception {
        super.setUp();

        ValueStack stack = ValueStackFactory.getFactory().createValueStack();
        ActionContext.setContext(new ActionContext(stack.getContext()));

        testAction = new TestAction();
        ActionContext.getContext().getValueStack().push(testAction);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ActionContext.setContext(null);
    }
}
