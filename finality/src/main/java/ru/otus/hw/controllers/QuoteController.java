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
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.QuoteCreateUpdateRequest;
import ru.otus.hw.dto.QuoteDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.QuoteRepository;
import ru.otus.hw.services.QuoteService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quotes")
public class QuoteController
{

    private final QuoteService quoteService;

    private final BookRepository bookRepository;

//    private final QuoteRepository quoteRepository;


    @GetMapping("/{bookId}")
    public String getAllQuotesByBook(@PathVariable("bookId") Long bookId, Model model) {
        List<QuoteDto> quotes = quoteService.findByBookId(bookId);
        model.addAttribute("bookTitle", getBookTitle(bookId));
        model.addAttribute("quotes", quotes);
        return "/quotes/get-all";
    }

    @GetMapping("/create/{bookId}")
    public String getCreateQuotePage(@PathVariable("bookId") Long bookId, Model model) {
        QuoteCreateUpdateRequest quote = new QuoteCreateUpdateRequest();
        quote.setBookId(bookId);
        model.addAttribute("quote", quote);
        model.addAttribute("bookTitle", getBookTitle(bookId));
        return "/quotes/create-form";
    }

    private String getBookTitle(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id '%s' not found".formatted(bookId)))
                .getTitle();
    }

    @PostMapping("/create")
    public String createQuote(@ModelAttribute(name = "quote") QuoteCreateUpdateRequest request, Model model) {

        quoteService.insert(request.getBookId(), request.getText());
        return "redirect:/quotes/" + request.getBookId();
    }

    @GetMapping("/edit/{id}")
    public String getUpdateQuotePage(@PathVariable("id") Long id, Model model) {
        QuoteDto quoteDto = quoteService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quote with id %d not found".formatted(id)));

        model.addAttribute("quote", quoteDto);
        return "/quotes/edit-form";
    }

    @PostMapping("/edit/{id}")
    public String updateQuote(@PathVariable("id") Long id,
                                @ModelAttribute("quote") QuoteCreateUpdateRequest request) {
        quoteService.update(id, request.getText());
        return "redirect:/quotes/" + request.getBookId();
    }

    @PostMapping("/delete/{id}")
    public String deleteQuote(@PathVariable("id") Long id) {
        quoteService.deleteById(id);
        return "redirect:/books/all";
    }
//    @GetMapping("/search")
//    public String findBooksByQuoteText(@RequestParam(name = "quoteText") String quoteText, Model model) {
//        List<Book> books = quoteRepository.getBooksByQuoteText(quoteText);
//        model.addAttribute("books", books);
//        return "/books/get-book";
//    }
//
//    @GetMapping("/search")
//    public String findBooksByQuoteText(@RequestParam String tagText, Model model) {
//        quoteService.getBooksByQuoteText()
//        List <BookDto> books = bookService.findBooksByTagText(tagText);
//        model.addAttribute("books", books);
//        return "/books/get-book";
//    }

}