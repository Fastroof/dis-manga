import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {MatDialog} from '@angular/material/dialog';
import {Order} from '../../shared/interfaces/order';
import {environment} from '../../../environments/environment';
import {OrderEditDialogComponent} from './order-edit-dialog/order-edit-dialog.component';

@Component({
  selector: 'app-dashboard-admin-orders',
  templateUrl: './dashboard-admin-orders.component.html',
  styleUrls: ['./dashboard-admin-orders.component.scss']
})
export class DashboardAdminOrdersComponent implements OnInit {

  orders: MatTableDataSource<Order> = new MatTableDataSource<Order>();

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
    fetch(environment.order + '/orders')
      .then((response) => response.json())
      .then((data: Order[]) => this.orders = new MatTableDataSource<Order>(data.sort((a, b) => {
        return b.id - a.id;
      })));
  }

  getLink(id: number): string {
    return environment.order + '/order/' + id;
  }

  // tslint:disable-next-line:typedef
  editOrder(id: number, status: string) {
    const dialogRef = this.dialog.open(OrderEditDialogComponent, {
      width: '280px',
      data: {id, status}
    });
    dialogRef.afterClosed().subscribe(() => window.location.reload());
  }

}
