import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'll-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss']
})
export class LoaderComponent implements OnInit {
  @Input() center: any;
  show = true;

  constructor() { }

  ngOnInit(): void {
    setTimeout(() => {
      this.show = false;
    }, 2500);
  }
}
