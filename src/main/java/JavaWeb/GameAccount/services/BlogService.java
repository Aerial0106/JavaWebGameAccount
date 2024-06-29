package JavaWeb.GameAccount.services;

import JavaWeb.GameAccount.model.*;
import JavaWeb.GameAccount.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@Transactional
public class BlogService {
    @Autowired
    private final BlogRepository blogRepository;
    public BlogService(BlogRepository blogRepository){
        this.blogRepository=blogRepository;
    }
    public List<Blog> findAllBlogs() {
        return blogRepository.findAll();
    }
    }

