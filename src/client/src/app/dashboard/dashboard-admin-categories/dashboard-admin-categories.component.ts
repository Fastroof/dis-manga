import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable, MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {CategoryDialogComponent} from './category-dialog/category-dialog.component';
import {Category} from '../../shared/interfaces/category';
import {CategoryEditDialogComponent} from './category-edit-dialog/category-edit-dialog.component';
import {CategoryDeleteDialogComponent} from './category-delete-dialog/category-delete-dialog.component';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-dashboard-admin-categories',
  templateUrl: './dashboard-admin-categories.component.html',
  styleUrls: ['./dashboard-admin-categories.component.scss']
})
export class DashboardAdminCategoriesComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  categories: MatTableDataSource<Category> = new MatTableDataSource<Category>();

  @ViewChild(MatTable) table: MatTable<Category> | undefined;

  ngOnInit(): void {
    fetch(environment.storage + '/categories')
      .then((response) => response.json())
      .then((data: Category[]) => this.categories = new MatTableDataSource<Category>(data.sort((a, b) => {
        return a.id - b.id;
      })));
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.categories.filter = filterValue.trim().toLowerCase();
  }

  addCategory(): void {
    const dialogRef = this.dialog.open(CategoryDialogComponent, {
      width: '280px'
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  editCategory(id: number, name: string): void {
    const dialogRef = this.dialog.open(CategoryEditDialogComponent, {
      width: '280px',
      data: {id, name}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

  deleteCategory(id: number): void {
    const dialogRef = this.dialog.open(CategoryDeleteDialogComponent, {
      width: '280px',
      data: id
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }
}
