package com.mmrath.sapp.web.rest.core;

import com.mmrath.sapp.domain.core.User;
import com.mmrath.sapp.service.core.UserService;
import com.mmrath.sapp.web.rest.rsql.RsqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class UserResource {

  private final Logger logger = LoggerFactory.getLogger(UserResource.class);

  private final UserService userService;

  @Autowired
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  @ResponseBody
  public User findById(@PathVariable("id") Long id) {
    User user = userService.findUser(id);
    return user;
  }

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public Page<User> findAll(String query, Pageable pageRequest) {
    logger.info("Page request:{}", pageRequest);
    logger.info("User search criteria:{}", query);
    return userService.findUsers(RsqlUtils.parse(query),pageRequest);
  }

  @RequestMapping(method = RequestMethod.POST)
  @ResponseBody
  public User create(@Valid @RequestBody User user) {
    logger.debug("User to create:{}", user);
    userService.createUser(user);
    return user;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public User update(@PathVariable("id") Long id, @RequestBody User user) {
    logger.debug("User to update:{}", user);
    user = userService.updateUser(user);
    return user;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  public Long delete(@PathVariable("id") Long id) {
    logger.debug("User to delete:{}", id);
    return userService.deleteUser(id);
  }
}
