/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.ui.Model;
// import org.springframework.security.core.Authentication;

/**
 * Controller for "/".
 *
 * @author Joe Grandja
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	// @GetMapping("/")
    // public String index(Model model, Authentication authentication) {
    //     if (authentication != null) {
    //         model.addAttribute("username", authentication.getName());
    //         model.addAttribute("authorities", authentication.getAuthorities());
    //     }
    //     return "index";
    // }

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')") // 只有管理员可访问
	public String admin() {
		return "admin";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')") // 用户可访问
	public String user() {
		return "user";
	}
}
