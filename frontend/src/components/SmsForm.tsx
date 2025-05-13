import React, { useState } from 'react';
import {
  TextField,
  Button,
  Box,
  Paper,
  Typography,
  CircularProgress,
} from '@mui/material';
import SendIcon from '@mui/icons-material/Send';

interface SmsFormProps {
  onSubmit: (message: string, phoneNumber: string) => void;
  loading: boolean;
}

const SmsForm: React.FC<SmsFormProps> = ({ onSubmit, loading }) => {
  const [message, setMessage] = useState<string>('');
  const [phoneNumber, setPhoneNumber] = useState<string>('');
  const [error, setError] = useState<string>('');

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError('');

    if (!message.trim()) {
      setError('Message is required');
      return;
    }

    onSubmit(message, phoneNumber);
  };

  const characterCount = message.length;
  const estimatedParts = Math.ceil(characterCount / 140); // Rough estimate

  return (
    <Paper elevation={3} sx={{ p: 4, maxWidth: 600, mx: 'auto' }}>
      <Typography variant="h5" gutterBottom>
        Send SMS Message
      </Typography>
      
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
        <TextField
          fullWidth
          label="Phone Number (Optional)"
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
          margin="normal"
          placeholder="1234567890"
        />
        
        <TextField
          fullWidth
          label="Message"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          margin="normal"
          multiline
          rows={4}
          required
          placeholder="Enter your message here..."
          error={!!error}
          helperText={error || `${characterCount} characters (approx. ${estimatedParts} part${estimatedParts !== 1 ? 's' : ''})`}
        />

        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Typography variant="body2" color="text.secondary">
            Messages over 160 characters will be split automatically
          </Typography>
          
          <Button
            type="submit"
            variant="contained"
            endIcon={loading ? <CircularProgress size={20} color="inherit" /> : <SendIcon />}
            disabled={loading}
          >
            {loading ? 'Sending...' : 'Send SMS'}
          </Button>
        </Box>
      </Box>
    </Paper>
  );
};

export default SmsForm;