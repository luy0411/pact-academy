package br.ce.wcaquino.consumer.tasks.service;

import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.utils.RequestHelper;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TasksConsumerTest {

    public static final String URL = "http://invalid.com";

    @InjectMocks
    private TasksConsumer consumer = new TasksConsumer(URL);

    @Mock
    private RequestHelper helper;

    @Test
    public void shouldGetAnExistingTask() throws IOException {
        //given
        Map<String, String> mock = new HashMap<>();
        mock.put("id", "1");
        mock.put("task", "teste");
        mock.put("dueDate", "2024-01-01");

        Mockito.when(helper.get(URL + "/todo/1")).thenReturn(mock);

        //when
        Task task = consumer.getTask(1L);

        //than
        Assert.assertNotNull(task);
        Assert.assertThat(task.getId(), CoreMatchers.is(1L));
    }
}
