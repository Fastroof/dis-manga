export interface ProductCreate {
  id: number;
  category_id: number | null;
  name: string;
  info: string;
  price: number;
}
