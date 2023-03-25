export interface Menu {
  path: string;
  name: string;
}

export const menuList: Menu[] = [
  {
    path: '/products',
    name: 'Товари'
  },
  {
    path: '/contact',
    name: 'Контакти'
  },
  {
    path: '/gallery',
    name: 'Галерея'
  }
];
