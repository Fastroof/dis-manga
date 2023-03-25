import {ProductImage} from './product-image';

export interface Product {
  id: number;
  category_id: number | null;
  images: ProductImage[] | any;
  name: string;
  info: string;
  price: number;
  available: number;
}
