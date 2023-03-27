import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Book} from '../../shared/interfaces/book';
import {BookFile} from '../../shared/interfaces/book-file';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

@Component({
  selector: 'book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {
  book: Book | undefined;
  bookFiles: BookFile[] | undefined;

  constructor(private activatedRoute: ActivatedRoute,
              private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(d => {
      // tslint:disable-next-line:no-string-literal
      this.book = d['book'];
      // tslint:disable-next-line:no-string-literal
      this.bookFiles = d['bookFiles'];
    });
  }

  previewUrl(bookFile: BookFile): SafeResourceUrl  {
    return this.sanitizer.bypassSecurityTrustResourceUrl('https://drive.google.com/file/d/' + bookFile.google_drive_id + '/preview');
  }
}
