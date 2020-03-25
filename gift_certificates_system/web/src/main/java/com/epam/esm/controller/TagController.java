package com.epam.esm.controller;

import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Tag rest controller
 */
@RestController
@RequestMapping(value = "/tags", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class TagController {
    private TagService tagService;

    /**
     * Constructor of certificate controller
     *
     * @param tagService service which provides actions with tags
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    /**
     * Method for getting all tags from database
     *
     * @return list of tags
     */
    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    public List<TagDto> getTagByName(@RequestParam(required = false) Map<String, String> searchParams) {
        return tagService.getByParams(searchParams);
    }

    /**
     * Method for getting by id
     *
     * @param id positive id of desirable tag
     * @return tag with desired id
     */
    @GetMapping(value = "/{id}")
    @Secured({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    public TagDto getTagById(@PathVariable("id") final Long id) {
        return tagService.getById(id);
    }

    /**
     * Method for saving tag
     *
     * @param tagDto should valid object for saving
     * @return saved certificate from database
     */
    @PostMapping
    @Secured("ROLE_ADMINISTRATOR")
    public TagDto saveTag(@RequestBody(required = false) TagDto tagDto) {
        return tagService.save(tagDto);
    }

    /**
     * Method for deleting tag by id
     *
     * @param id positive id of desirable tag
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMINISTRATOR")
    public void deleteTagById(@PathVariable("id") final Long id) {
        tagService.deleteById(id);
    }

    /**
     * method for handling bad request
     */
    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Secured("ROLE_ADMINISTRATOR")
    public void missingPathVariable() {
        throw new ValidatorException("", ErrorCode.MISSING_PATH_VARIABLE);
    }

    /**
     * @return tag according to task
     */
    @GetMapping(value = "/popular")
    @Secured({"ROLE_USER", "ROLE_ADMINISTRATOR"})
    public TagDto getPopularTag() {
        return tagService.getPopularTag();
    }
}
