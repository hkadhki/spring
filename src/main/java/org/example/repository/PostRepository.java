package org.example.repository;

import org.springframework.stereotype.Repository;
import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository {


 private AtomicInteger idCounter = new AtomicInteger();

  private List<Post> list = new ArrayList<>();
  public List<Post> all() {
    return list;
  }

  public Optional<Post> getById(long id) {
    try {
        return Optional.of(list.get((int) id - 1));
    }catch (IndexOutOfBoundsException e){
      return Optional.empty();
    }
  }

  public Post save(Post post) {
    int id = (int)post.getId();
    if (id == 0){
      idCounter.getAndIncrement();
      list.add(post);
    }else {
      if (idCounter.get() > id){
        list.set(id,post);
      }else{
        throw new NotFoundException();
      }
    }
    return post;
  }

  public void removeById(long id) {
    list.remove((int)id);
  }
}
