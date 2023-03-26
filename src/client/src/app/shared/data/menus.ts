export interface Menu {
  path: string;
  name: string;
}

export const menuList: Menu[] = [
  {
    path: '/books',
    name: 'Каталог'
  },
  {
    path: '/contact',
    name: 'Контакти'
  }
];
