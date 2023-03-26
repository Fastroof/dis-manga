export interface Menu {
  path: string;
  name: string;
}

export const menuList: Menu[] = [
  {
    path: '/products',
    name: 'Каталог'
  },
  {
    path: '/contact',
    name: 'Контакти'
  }
];
