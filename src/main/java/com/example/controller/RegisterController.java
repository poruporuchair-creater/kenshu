package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.model.UserDto;
import com.example.service.UserService;

@Controller // このクラスがWebコントローラーであることを示します
public class RegisterController {

    // Spring が自動的に UserService の実装を注入します
    @Autowired
    private UserService userService;

    @GetMapping("/register") // "/register"というURLに対するGETリクエストを処理します
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView(); // ModelAndViewオブジェクトを作成します
        mav.addObject("user", new UserDto()); // 新しいUserDtoオブジェクトを"ユーザー"という名前で追加します
        mav.setViewName("register"); // 表示するビュー（HTMLファイル）の名前を"register"に設定します
        return mav; // ModelAndViewオブジェクトを返します
    }

    @PostMapping("/register") // "/register"というURLに対するPOSTリクエストを処理します
    public String register(@ModelAttribute UserDto userDto) {
        User existing = userService.findByUsername(userDto.getUsername()); // ユーザー名で既存のユーザーを検索します
        if(existing != null){
            // ユーザーが既に存在する場合の処理
            return "register"; // ユーザーが存在するため、再度登録画面を表示します
        }
        userService.save(userDto); // ユーザーが存在しない場合、新しいユーザーを保存します
        return "login"; // 登録が成功した場合、ログイン画面を表示します
    }
}
