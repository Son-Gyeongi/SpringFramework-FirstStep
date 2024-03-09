package com.example.quiz.controller;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    /**
     * DI 대상
     */
    @Autowired
    QuizService service;

    /**
     * form-backing bean의 초기화 - form-backing:양식 지원
     * 입력 체크(유효성 검사)
     */
    @ModelAttribute
    public QuizForm setUpForm() {
        QuizForm form = new QuizForm();
        // 라디오 버튼의 초깃값 설정
        form.setAnswer(true);
        return form;
    }

    /**
     * Quiz 목록 표시
     */
    @GetMapping
    public String showList(QuizForm quizForm, Model model) {
        // 신규 등록 설정
        quizForm.setNewQuiz(true);

        // 퀴즈 목록 취득
        Iterable<Quiz> list = service.selectAll();

        // 표시용 모델에 저장
        model.addAttribute("list", list);
        model.addAttribute("title", "등록 폼");

        return "crud";
    }

    /**
     * Quiz 데이터를 1건 등록
     */
    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {
        // Form에서 Entity로 넣기
        Quiz quiz = new Quiz();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        // 입력 체크
        if (!bindingResult.hasErrors()) {
            service.insertQuiz(quiz);
            // 리다이렉트할 화면에 전달할 값을 설정 - Flash Scope 범위로 한 번의 리다이렉트에서만 유효
            redirectAttributes.addFlashAttribute("complete", "등록이 완료되었습니다");
            return "redirect:/quiz";
        } else {
            // 에러가 발생한 경우에는 목록 표시로 변경
            return showList(quizForm, model);
        }
    }

    /**
     * Quiz 데이터를 1건 취득해서 폼 안에 표시
     */
    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
        // Quiz를 취득(Optional로 래핑)
        Optional<Quiz> quizOpt = service.selectOneById(id);

        // QuizForm에 채워넣기
        Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));

        // QuizForm이 null이 아니라면 값을 취득
        if (quizFormOpt.isPresent()) {
            quizForm = quizFormOpt.get();
        }

        // 변경용 모델 생성
        makeUpdateModel(quizForm, model);
        return "crud";
    }

    /**
     * 변경용 모델 생성
     * 변경 화면을 만들기 위한 메서드
     */
    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "변경 폼");
    }

    /**
     * id를 키로 사용해 데이터를 변경
     */
    @PostMapping("/update")
    public String update(
            @Validated QuizForm quizForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // QuizForm에서 Quiz로 채우기
        Quiz quiz = makeQuiz(quizForm);
        // 입력 체크
        if (!result.hasErrors()) {
            // 변경 처리, Flash scope를 사용해서 리다이렉트 설정
            service.updateQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "변경이 완료되었습니다");
            // 변경 화면을 표시
            return "redirect:/quiz/" + quiz.getId();
        } else {
            // 변경용 모델을 생성
            makeUpdateModel(quizForm, model);
            return "crud";
        }
    }

    // ---------- [아래는 Form과 도메인 객체를 다시 채우기] ----------

    /**
     * QuizForm에서 Quiz로 다시 채우기, 반환값으로 돌려줌
     */
    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    /**
     * Quiz에서 QuizForm로 다시 채우기, 반환값으로 돌려줌
     */
    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false); // 새로운 퀴즈가 아니다를 명시
        return form;
    }

    /**
     * id를 키로 사용해 데이터를 삭제
     */
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // 퀴즈 정보를 1건 삭제하고 리다이렉트
        service.deleteQuizById(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "삭제가 완료했습니다");
        return "redirect:/quiz";
    }
}