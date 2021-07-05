package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

  @Autowired
  private TaskRepository taskRepository;

  @GetMapping("/tasks/")
  public List<Task> list() {
    Iterable<Task> taskIterable = taskRepository.findAll();
    ArrayList<Task> tasks = new ArrayList<>();
    for(Task task : taskIterable){
      tasks.add(task);
    }
    return tasks;
  }

  @PostMapping("/tasks/")
  public int add(Task task) {
    Task newTask = taskRepository.save(task);
    return newTask.getId();
  }

  @DeleteMapping("/tasks/")
  public ResponseEntity deleteAll() {
    taskRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/tasks/{id}")
  public ResponseEntity get(@PathVariable int id) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
  }

  @DeleteMapping("/tasks/{id}")
  public ResponseEntity delete(@PathVariable int id) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {
      taskRepository.deleteById(id);
    }
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/tasks/{id}")
  public ResponseEntity update(Task newTask, @PathVariable int id) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (!optionalTask.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {
      newTask.setId(id);
      taskRepository.save(newTask);
    }
    return ResponseEntity.noContent().build();
  }



}

