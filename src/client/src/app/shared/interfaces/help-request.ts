export interface HelpRequest {
  id: number;
  created_at: string;
  text: string;
  email: string;
  status: number;
  moderator_id: number | null;
}
