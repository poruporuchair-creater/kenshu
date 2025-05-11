package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring Bootアプリケーションであることを示します
public class LibraryManagementSystemApplication { // アプリケーションのメインクラス

	public static void main(String[] args) { // Javaアプリケーションのエントリーポイント（最初に実行されるメソッド）
		SpringApplication.run(LibraryManagementSystemApplication.class, args); // Spring Bootアプリケーションを起動します
	}
}
