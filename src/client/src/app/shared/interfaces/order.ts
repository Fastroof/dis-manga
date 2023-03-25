import {OrderItemForOrder} from './order-item-for-order';

export interface Order {
  id: number;
  status: string;
  order_items: OrderItemForOrder[];
  timestamp: string;
  user_id: number;
  email: string;
}
