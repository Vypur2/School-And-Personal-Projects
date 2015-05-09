#if !defined(CLASH_H)
///////////////////////

// timing, input, bitmap, sound buffer

typedef float real32;
typedef double real64;

#define ArrayCount(Array) (sizeof(Array) / sizeof((Array)[0]))

struct game_offscreen_buffer
{
    void *Memory;
    int Width;
    int Height;
    int Pitch;
    int BytesPerPixel;
};
struct game_button_state 
{
	int HalfTransitionCount;
	bool EndedDown;
};

struct game_state
{
	int xoffset;
	int yoffset;

	int PlayerX;
	int PlayerY;

	real32 tJump;
};

struct game_controller_input
{
	real32 AverageX;
	real32 AverageY;

	real32 EndX;
	real32 EndY;

	union 
	{
		game_button_state Buttons[11];
		struct
		{
			game_button_state Up;
			game_button_state Jump;
			game_button_state Down;
			game_button_state Left;
			game_button_state Right;
			game_button_state ActionUp;
			game_button_state ActionDown;
			game_button_state ActionLeft;
			game_button_state ActionRight;
			game_button_state RightShoulder;
			game_button_state LeftShoulder;
		};
	};
	

};

struct game_input
{
	int32 MouseX, MouseY, MouseZ;

	game_controller_input Controllers[5];
};

void GameUpdateAndRender(game_offscreen_buffer *Buffer,game_input *Input);

#define CLASH_H
#endif