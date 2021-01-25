package com.app.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.dto.PostDto;
import com.app.blog.repository.PostRepository;
import com.app.blog.security.PostService;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@PostMapping
	public ResponseEntity createPost(@RequestBody PostDto postDto) {
		postService.createPost(postDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<PostDto>> showAllPosts() {
		return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody String id) {
		return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
	}

	/*
	 * @PutMapping("/update/{id}") public PostDto updatePost(@RequestBody PostDto
	 * postDto, @PathVariable Long id, String title, String content) {
	 * postDto.setId(id); postDto.setTitle(title); postDto.setContent(content);
	 * postRepository.save(postDto); return postDto; }
	 */
	//
	@PutMapping("/update/{id}")
	public ResponseEntity updatePost(@RequestBody PostDto postDto, @PathVariable String title, String content) {
		postDto.setTitle(title);
		postDto.setContent(content);
		postService.updatePost(postDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") String id) {
		try {
			postRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	// need to update UI
}
