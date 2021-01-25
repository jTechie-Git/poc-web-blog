package com.app.blog.security;

import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.blog.dto.PostDto;
import com.app.blog.exception.PostNotFoundException;
import com.app.blog.model.Post;
import com.app.blog.repository.PostRepository;
import com.app.blog.service.AuthService;

@Service
public class PostService {

	@Autowired
	private AuthService authService;
	@Autowired
	private PostRepository postRepository;

	@Transactional
	public List<PostDto> showAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(this::mapFromPostToDto).collect(toList());
	}

	@Transactional
	public void createPost(PostDto postDto) {
		Post post = mapFromDtoToPost(postDto);
		postRepository.save(post);
	}

	@Transactional
	public PostDto readSinglePost(String id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
		return mapFromPostToDto(post);
	}
	
	//
    @Transactional
    public void updatePost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }
	  

	@Transactional
	public void deletePost(String id) {
		postRepository.deleteById(id);
	}

	//

	private PostDto mapFromPostToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getCreatedBy());
		return postDto;
	}

	private Post mapFromDtoToPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User loggedInUser = authService.getCurrentUser()
				.orElseThrow(() -> new IllegalArgumentException("User Not Found"));
		post.setCreationDate(Instant.now());
		post.setCreatedBy(loggedInUser.getUsername());
		post.setModificationDate(Instant.now());
		return post;
	}
}
