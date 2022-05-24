package com.smartclide.dbapi.controller;

import com.smartclide.dbapi.model.GitCredentials;
import com.smartclide.dbapi.repository.GitCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GitCredentialsController {

    @Autowired
    private GitCredentialsRepository repository;

    @GetMapping("/git_credentials")
    public List<GitCredentials> getAllGitCredentials() {
        return repository.findAll();
    }

    @GetMapping("/git_credentials/{id}")
    public Optional<GitCredentials> getGitCredentials(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping("/git_credentials")
    public ResponseEntity<GitCredentials> createGitCredentials(@RequestBody GitCredentials gitCredentials) {
        try {
            GitCredentials _gitCredentials = repository.save(gitCredentials);
            return new ResponseEntity<>(_gitCredentials, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/git_credentials/{id}")
    public ResponseEntity<GitCredentials> updateGitCredentials(@PathVariable("id") String id, @RequestBody GitCredentials gitCredentials) {
        try {
            Optional<GitCredentials> gitCredentialsData = repository.findById(id);

            if (gitCredentialsData.isPresent()) {
                GitCredentials _gitCredentials = gitCredentialsData.get();

                _gitCredentials.setUser_id(gitCredentials.getUser_id());
                _gitCredentials.setType(gitCredentials.getType());
                _gitCredentials.setUrl(gitCredentials.getUrl());
                _gitCredentials.setUsername(gitCredentials.getUsername());
                _gitCredentials.setToken(gitCredentials.getToken());

                return new ResponseEntity<>(repository.save(_gitCredentials), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/git_credentials/{id}")
    public ResponseEntity<?> deleteGitCredentials(@PathVariable("id") String id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
