//
typedef int8_t int8;
typedef int32_t int32;
typedef int64_t int64;
typedef int16_t int16;

typedef uint8_t uint8;
typedef uint32_t uint32;
typedef uint64_t uint64;
typedef uint16_t uint16;

#define local_persist static
#define PI 3.141592659

#include "clash.h"
#include <tgmath.h>
#include <cmath>

void DrawRectangle(game_offscreen_buffer *Buffer, real32 minX, real32 minY, real32 maxX, real32 maxY)
{
    
}

void RenderGradient(game_offscreen_buffer Buffer, int XOffset, int YOffset)
{
    int Width = Buffer.Width;
    int Height = Buffer.Height;

    uint8 *Row = (uint8 *)Buffer.Memory;
    for(int y = 0;y<Buffer.Height;y++)
    {
        uint32 *Pixel = (uint32 *)Row;
        for (int x = 0;x<Buffer.Width;x++)
        {
            //structure of the pixel in memory
            uint8 Blue = (x+ XOffset);
            uint8 Green = (y+YOffset);

            *Pixel++ = ((Green << 16) | Blue);
        }
        Row += Buffer.Pitch;
    }
}
game_state *GameStartup()
{
    game_state *GameState = new game_state;
    if (GameState){
        GameState->xoffset = 0;
        GameState->yoffset = 0;
    }

    return GameState;
}

void GameShutdown(game_state *GameState)
{
    delete GameState;
}

void RenderPlayer(game_offscreen_buffer Buffer,int PlayerX, int PlayerY)
{
    uint32 Color = 0xFFFFFFF;
    int Top = PlayerY;
    int Bottom = PlayerY + 15;
    for (int X = PlayerX;X < PlayerX+10;++X)
    {
        uint8 *Pixel = ((uint8 *)Buffer.Memory + X*4 + Top*Buffer.Pitch);
        for (int Y = Top; Y < Bottom;++Y)
        {
            *(uint32 *)Pixel = Color;
            Pixel += Buffer.Pitch;
        }
    }
}
    
void FreeMemory(void* Memory){
    if (Memory){
        VirtualFree(Memory,0,MEM_RELEASE);
    }
}

void GameUpdateAndRender(game_state *GameState, game_offscreen_buffer Buffer, game_input *Input){

    if (Input->Controllers[0].Up.EndedDown){
      //  GameState->yoffset +=10;
        GameState->PlayerY += -10;

    } else {
        ;
    }

    if (Input->Controllers[0].Down.EndedDown){
       // GameState->yoffset +=-10;
        GameState->PlayerY += 10;
    } else {
        ;
    }

    if (Input->Controllers[0].Right.EndedDown){
        //GameState->xoffset +=-10;
        GameState->PlayerX += 10;
    } else {
        ;
    }

    if (Input->Controllers[0].Left.EndedDown){
        //GameState->xoffset +=10;
        GameState->PlayerX += -10;
    } else {
        ;
    }

    if (Input->Controllers[0].Jump.EndedDown){
        GameState->PlayerY -= 30;
    }

  //  RenderPlayer(Buffer,Input->MouseX,Input->MouseY);
	RenderGradient(Buffer,GameState->xoffset,GameState->yoffset);
    RenderPlayer(Buffer,GameState->PlayerX,GameState->PlayerY);
}

//void MLoop(void) 
//{
//	void *FileContesnt = (LoadFile("Foo.bmp"));
//
//}

