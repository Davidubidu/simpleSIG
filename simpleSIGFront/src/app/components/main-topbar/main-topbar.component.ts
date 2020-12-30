import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-main-topbar',
  templateUrl: './main-topbar.component.html',
  styleUrls: ['./main-topbar.component.css'],
})
export class MainTopbarComponent implements OnInit {
  @Input() username: string;
  @Input() title: string;

  constructor() {}

  ngOnInit(): void {}
}
