#include <stdio.h>
#include <mpi.h>

int main ( int argc, char *argv[] )
{
    int i,p;
    char name[20];

    MPI_Init(&argc,&argv);
    MPI_Comm_size(MPI_COMM_WORLD,&p);
    MPI_Comm_rank(MPI_COMM_WORLD,&i);

    if (i == 0) {
        printf("Enter your name: ");
        if (scanf("%20s", name) != 1) {
            fprintf(stderr, "Error reading name.\n");
            MPI_Abort(MPI_COMM_WORLD, 1);
        }
    }

    MPI_Bcast(name, 20, MPI_CHAR, 0, MPI_COMM_WORLD);

    printf("Hello %s! Greetings from processor %d out of %d.\n", name, i, p);

    MPI_Finalize();

    return 0;
}