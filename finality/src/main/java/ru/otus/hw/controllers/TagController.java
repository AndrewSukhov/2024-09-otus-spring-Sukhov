package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.TagCreateUpdateRequest;
import ru.otus.hw.dto.TagDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.TagService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    private final BookRepository bookRepository;

    @GetMapping("/{bookId}")
    public String getAllTagsByBook(@PathVariable("bookId") Long bookId, Model model) {
        List<TagDto> tags = tagService.findByBookId(bookId);
        model.addAttribute("bookTitle", getBookTitle(bookId));
        model.addAttribute("tags", tags);
        return "/tags/get-all";
    }

    @GetMapping("/create/{bookId}")
    public String getCreateTagPage(@PathVariable("bookId") Long bookId, Model model) {
        TagCreateUpdateRequest tag = new TagCreateUpdateRequest();
        tag.setBookId(bookId);
        model.addAttribute("tag", tag);
        model.addAttribute("bookTitle", getBookTitle(bookId));
        return "/tags/create-form";
    }

    private String getBookTitle(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book with id '%s' not found".formatted(bookId)))
            .getTitle();
    }

    @PostMapping("/create")
    public String createTag(@ModelAttribute(name = "tag") TagCreateUpdateRequest request, Model model) {

        tagService.insert(request.getBookId(), request.getText());
        return "redirect:/tags/" + request.getBookId();
    }

    @GetMapping("/edit/{id}")
    public String getUpdateTagPage(@PathVariable("id") Long id, Model model) {
        TagDto tagDto = tagService.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tag with id %d not found".formatted(id)));

        model.addAttribute("tag", tagDto);
        return "/tags/edit-form";
    }

    @PostMapping("/edit/{id}")
    public String updateTag(@PathVariable("id") Long id,
                                @ModelAttribute("tag") TagCreateUpdateRequest request) {
        tagService.update(id, request.getText());
        return "redirect:/tags/" + request.getBookId();
    }

    @PostMapping("/delete/{id}")
    public String deleteTag(@PathVariable("id") Long id) {
        tagService.deleteById(id);
        return "redirect:/books/all";
    }

    @PostMapping("/search")
    public String searchByText(@RequestParam String tagSearchText, Model model) {
        List<TagDto> quotes = tagService.findByTextContaining(tagSearchText);
        model.addAttribute("tags", quotes);
        model.addAttribute("tagSearchText", tagSearchText);
        return "tags/get-book-by-tag.html";
    }

}