import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ChangeLanguage } from './components/change-language/change-language';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet,ChangeLanguage],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('webdipatch');
   constructor(private translate: TranslateService) {
   const savedLang = localStorage.getItem('preferredLang') || 'ru';
    this.translate.use(savedLang);
   }
}
