#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

#define v 1 /* verbose flag, output if 1,
                          no output if 0 */

int main ( int argc, char *argv[] )
{
   int myid, j, *data, *tosum, local_sum=0, global_sum, p, elements_per_proc, remainder, start, end;

   MPI_Init(&argc,&argv);
   MPI_Comm_rank(MPI_COMM_WORLD,&myid);
   MPI_Comm_size(MPI_COMM_WORLD, &p);

   elements_per_proc = 200 / p;
   remainder = 200 % p;

   tosum = (int*)malloc((elements_per_proc + (myid < remainder ? 1 : 0)) * sizeof(int));

   if(myid==0) /* manager allocates 
                  and initializes the data */
   {
      data = (int*)calloc(200,sizeof(int));
      for (j=0; j<200; j++) data[j] = j+1;
      if(v>0)
      {
         printf("The data to sum : ");
         for (j=0; j<200; j++) printf(" %d",data[j]); printf("\n");
      }
   }

   start = myid * elements_per_proc + (myid < remainder ? myid : remainder);
   end = start + elements_per_proc + (myid < remainder ? 1 : 0);

   if (myid==0) {
        for (int i=1; i<p; i++) {
            int s = i * elements_per_proc + (i < remainder ? i : remainder);
            int e = s + elements_per_proc + (i < remainder ? 1 : 0);
            MPI_Send(&data[s], e - s, MPI_INT, i, 0, MPI_COMM_WORLD);
        }
        for (j = start; j < end; j++) tosum[j - start] = data[j];
    } else {
        MPI_Recv(tosum, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

   if(v>0)
   {
      printf("Node %d has numbers to sum :",myid);
      for(j=0; j<end - start; j++) printf(" %d", tosum[j]);
      printf("\n");
   }

   local_sum = 0;
   for(j=0; j<end - start; j++) local_sum += tosum[j];
   if(v>0) printf("Node %d computes the sum %d\n",
                   myid,local_sum);

   MPI_Reduce(&local_sum, &global_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

   if(myid==0)
   {
      printf("The total sum : %d, which should be 20100.\n", global_sum);
   }
   MPI_Finalize();
   return 0;
}