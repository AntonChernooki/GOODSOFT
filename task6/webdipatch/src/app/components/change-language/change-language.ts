import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-change-language',
  imports: [],
  templateUrl: './change-language.html',
  styleUrl: './change-language.css',
})
export class ChangeLanguage {
  languages = ['ru', 'en'];
  currentLang: string;

  constructor(private translate: TranslateService) {
    const savedLang = localStorage.getItem('preferredLang') || 'ru';
    this.translate.use(savedLang);
    this.currentLang = savedLang;
  }

  switchLang(lang: string) {
    this.translate.use(lang);
    localStorage.setItem('preferredLang', lang);
    this.currentLang = lang;
  }
  toggleLang() {
    let newLang = '';
    if (this.currentLang === 'ru') {
      newLang = 'en';
    } else {
      newLang = 'ru';
    }

    this.switchLang(newLang);
  }
}
