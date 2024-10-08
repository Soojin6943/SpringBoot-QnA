package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import com.mysite.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

//import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
//	@ResponseBody
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
//		List<Question> questionList = this.questionService.getList();
//		
//		// 모델 객체는 자바 클래스와 템플릿 간의 연결고리 역할 (모델 객체에 값을 담아 두면 템플릿에서 그 값을 사용할 수 있음)	
//		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		// service에서 가져오고 controller에서 선언?하고 모델로 해서 템플릿 간 연결 후 사용?
		
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list"; //질문 저장 후 질문 목록으로 이동 
	}
}
