import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-main-topbar',
  templateUrl: './main-topbar.component.html',
  styleUrls: ['./main-topbar.component.css'],
})
export class MainTopbarComponent implements OnInit {
  @Input() username: string;
  @Input() logged: boolean;
  @Input() title: string;
  @Output() login = new EventEmitter();
  @Output() logout = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  clickLogin() {
    this.login.emit();
  }

  clickLogout() {
    this.logout.emit();
  }
}
