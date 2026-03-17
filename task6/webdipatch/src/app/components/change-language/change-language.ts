import { Component } from '@angular/core';
import { LanguageService } from '../../service/language-service';
@Component({
  selector: 'app-change-language',
  imports: [],
  templateUrl: './change-language.html',
  styleUrl: './change-language.css',
})
export class ChangeLanguageComponent {
  languages = ['ru', 'en'];
  currentLang: string;

  constructor(private languageService: LanguageService) {
    this.currentLang = this.languageService.getCurrentLanguage();
  }

  toggleLang() {
      this.languageService.toggleLang();
    this.currentLang = this.languageService.getCurrentLanguage();
  }
}
