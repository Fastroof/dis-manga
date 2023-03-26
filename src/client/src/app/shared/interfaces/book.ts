export interface Book {
  id: number;
  updated_at: string;
  created_at: string;
  tag_id: number;
  name: string;
  owner_id: number;
  link_to_cover: string | null;
}
