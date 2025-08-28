CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- CREATE TABLE users (
--        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--        email TEXT UNIQUE NOT NULL,
--        password_hash TEXT NOT NULL,
--        display_name TEXT NOT NULL,
--        role TEXT NOT NULL DEFAULT 'ROLE_USER',
--        created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
--        last_login_at TIMESTAMPTZ
-- );



CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    display_name TEXT NOT NULL,
    role TEXT NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    last_login_at TIMESTAMPTZ
    );



CREATE TABLE channels (
      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      name TEXT NOT NULL UNIQUE,
      type TEXT NOT NULL DEFAULT 'PUBLIC',
      created_by UUID NOT NULL REFERENCES users(id),
      created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE channel_members (
     channel_id UUID REFERENCES channels(id) ON DELETE CASCADE,
     user_id UUID REFERENCES users(id) ON DELETE CASCADE,
     role TEXT NOT NULL DEFAULT 'MEMBER',
     joined_at TIMESTAMPTZ NOT NULL DEFAULT now(),
     PRIMARY KEY (channel_id, user_id)
);

CREATE TABLE messages (
      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
      author_id UUID NOT NULL REFERENCES users(id),
      content TEXT NOT NULL,
      reply_to UUID REFERENCES messages(id),
      edited BOOLEAN NOT NULL DEFAULT FALSE,
      created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
      edited_at TIMESTAMPTZ
);

CREATE INDEX idx_messages_channel_created ON messages(channel_id, created_at DESC);

CREATE TABLE reactions (
       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
       message_id UUID NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
       user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
       emoji TEXT NOT NULL,
       created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
       UNIQUE(message_id, user_id, emoji)
);

CREATE TABLE files (
       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
       channel_id UUID NOT NULL REFERENCES channels(id) ON DELETE CASCADE,
       uploader_id UUID NOT NULL REFERENCES users(id),
       original_name TEXT NOT NULL,
       mime_type TEXT NOT NULL,
       size BIGINT NOT NULL,
       storage_path TEXT NOT NULL,
       sha256 TEXT NOT NULL,
       created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);