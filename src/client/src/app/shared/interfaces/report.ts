export interface Report {
  id: number;
  created_at: string;
  text: string;
  user_id: number;
  status: number;
  moderator_id: number | null;
  book_id: number;
}
