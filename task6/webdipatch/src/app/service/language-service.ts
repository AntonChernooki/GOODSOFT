import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private currentLang!: string ;
  
 constructor(private translate: TranslateService) {
    const savedLang = localStorage.getItem('preferredLang') || 'ru';
    this.switchLang(savedLang);
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

  getCurrentLanguage(): string {
    return this.currentLang;
  }

}
