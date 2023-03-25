import { Component, OnInit } from '@angular/core';
import {Group} from '../shared/interfaces/group';
import {GalleryImage} from '../shared/interfaces/gallery-image';
import $ from 'jquery';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.scss']
})
export class GalleryComponent implements OnInit {

  groups: Group[] = [];
  galleryImages: GalleryImage[] = [];

  constructor() {
  }

  ngOnInit(): void {
    fetch(environment.gallery + '/gallery')
      .then(r => r.text())
      .then(data => this.groups = JSON.parse(data));
    fetch(environment.gallery + '/gallery-images')
      .then(r => r.text())
      .then(data => this.galleryImages = JSON.parse(data));
  }

  // tslint:disable-next-line:typedef
  searchImg(id: any) {
      // @ts-ignore
    // tslint:disable-next-line:typedef
      $('.fj-gallery-item').filter(function() {
        if (id === -1) {
          $(this).toggle(true);
        } else {
          $(this).toggle($(this).find('.image').attr('alt') === id);
        }
      });
  }
}
