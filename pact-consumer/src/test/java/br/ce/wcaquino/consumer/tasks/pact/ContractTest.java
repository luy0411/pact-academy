package br.ce.wcaquino.consumer.tasks.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.tasks.service.TasksConsumer;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ContractTest {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Tasks", this);

    @Pact(consumer = "BasicConsumer")
    public RequestResponsePact createPact (PactDslWithProvider builder){
        return builder.given("There's a pact with id 1")
                      .uponReceiving("Retrieve task 1")
                            .path("/todo/1")
                            .method("GET")
                      .willRespondWith()
                            .status(200)
                            .body("{\"id\":1,\"task\":\"pact\",\"dueDate\":\"2024-01-01\"}")
                     .toPact();
    }

    @Test
    @PactVerification
    public void test() throws IOException {
        TasksConsumer consumer = new TasksConsumer(mockProvider.getUrl());
        System.out.println(mockProvider.getUrl());

        Task task = consumer.getTask(1L);

        assertThat(task.getId(), is(1l));
        assertThat(task.getTask(), is("pact"));
    }


}

